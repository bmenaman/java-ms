package com.bmenaman.app.cucumber;

import com.bmenaman.app.Application;
import com.bmenaman.app.cucumber.config.TestKafkaConfig;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(
    classes = {Application.class, TestKafkaConfig.class},
    properties = "spring.profiles.active=test")
@DirtiesContext
@EmbeddedKafka(
    partitions = 1,
    topics = {"numbers", "totals"},
    bootstrapServersProperty = "spring.kafka.bootstrap-servers")
@TestPropertySource(properties = {"spring.kafka.consumer.auto-offset-reset=earliest"})
public class NumberAdditionSteps {

  @Autowired
  @Qualifier("testKafkaTemplate")
  private KafkaTemplate<String, Integer> kafkaTemplate;

  private final List<Integer> receivedTotals = new CopyOnWriteArrayList<>();
  private CompletableFuture<Void> totalsReceived;
  private AtomicInteger expectedMessageCount = new AtomicInteger(0);

  @KafkaListener(
      topics = "totals",
      groupId = "cucumber-test-group",
      containerFactory = "testKafkaListenerContainerFactory")
  public void receiveTotals(Integer total) {
    receivedTotals.add(total);
    if (totalsReceived != null
        && !totalsReceived.isDone()
        && receivedTotals.size() >= expectedMessageCount.get()) {
      totalsReceived.complete(null);
    }
  }

  @Given("the service is ready to receive numbers")
  public void theServiceIsReadyToReceiveNumbers() {
    receivedTotals.clear();
    expectedMessageCount.set(0);
    totalsReceived = new CompletableFuture<>();
  }

  @Given("the current total is {int}")
  public void theCurrentTotalIs(Integer initialTotal) {
    // The total is managed by the service, we just verify it's working
  }

  @When("number {int} is received")
  public void numberIsReceived(Integer number) throws Exception {
    expectedMessageCount.set(1);
    CompletableFuture<SendResult<String, Integer>> sendResult =
        kafkaTemplate.send("numbers", number);
    sendResult.get(5, TimeUnit.SECONDS); // Wait for send to complete
    totalsReceived = new CompletableFuture<>();
  }

  @When("these numbers are received:")
  public void theseNumbersAreReceived(DataTable dataTable) throws Exception {
    List<Map<String, String>> rows = dataTable.asMaps();
    expectedMessageCount.set(rows.size());
    totalsReceived = new CompletableFuture<>();

    for (Map<String, String> row : rows) {
      Integer number = Integer.valueOf(row.get("number"));
      CompletableFuture<SendResult<String, Integer>> sendResult =
          kafkaTemplate.send("numbers", number);
      sendResult.get(5, TimeUnit.SECONDS); // Wait for send to complete
    }
  }

  @Then("total {int} should be published")
  public void totalShouldBePublished(Integer expectedTotal) throws Exception {
    totalsReceived.get(10, TimeUnit.SECONDS);
    assert receivedTotals.contains(expectedTotal)
        : "Expected total " + expectedTotal + " not found in received totals: " + receivedTotals;
  }

  @Then("these totals should be published:")
  public void theseTotalsShouldBePublished(DataTable dataTable) throws Exception {
    List<Map<String, String>> rows = dataTable.asMaps();
    totalsReceived.get(10, TimeUnit.SECONDS);

    assert receivedTotals.size() >= rows.size()
        : "Not enough totals received. Expected "
            + rows.size()
            + " but got "
            + receivedTotals.size();

    for (int i = 0; i < rows.size(); i++) {
      Integer expectedTotal = Integer.valueOf(rows.get(i).get("total"));
      assert receivedTotals.get(i).equals(expectedTotal)
          : "Total at position "
              + i
              + " was "
              + receivedTotals.get(i)
              + " but expected "
              + expectedTotal;
    }
  }
}
