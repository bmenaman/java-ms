# Java Microservice Implementation Plan

## Stage 1: Environment Setup and Verification
1. Check and install required dependencies
   - Check Docker: `docker --version`
   - Install Java 21 using jenv if not present
   - Install Docker if not present
   - Verification: All tools installed and accessible

2. Verify Java 21 installation
   - Run `java -version`
   - Expected: Java 21.x output
   - Configure jenv if needed

3. Setup Gradle wrapper
   - Create minimal build.gradle
   - Run `gradle wrapper` to generate wrapper files
   - Verify wrapper works: `./gradlew --version`
   - Expected: Gradle wrapper downloads and shows version

4. Verify Docker installation
   - Run `docker info`
   - Expected: Docker daemon responding

## Stage 2: Basic Gradle Project Setup
1. Initialize Gradle project structure
   - Create basic `build.gradle` with Java plugin
   - Configure Java 21 compatibility
   - Add JUnit 5 dependency
   - Verification: `gradle build` succeeds

2. Create first failing test
   - Create simple JUnit test class
   - Verification: `gradle test` fails as expected
   - Implement to make test pass
   - Verification: `gradle test` passes

## Stage 3: Core Dependencies Setup
1. Add and configure build dependencies
   - Spring Boot
   - Dagger
   - Log4j
   - PITest
   - JaCoCo
   - Checkstyle/Spotless for formatting
   - Verification: 
     - `gradle build` shows plugins applied in build output
     - JaCoCo generates coverage report
     - PITest generates mutation report
     - Checkstyle/Spotless shows formatting report

2. Add container build support
   - Configure Jib plugin
   - Verification: `gradle jib` builds and pushes container successfully

## Stage 4: Health Check API
1. Create health check test script
   - Write shell script to test endpoint
   - Include Docker container health check
   - Verification: Script ready to test endpoint

2. Implement basic Spring Boot application
   - Create main Application class
   - Add health check controller
   - Write controller test
   - Verification: 
     - Application builds
     - Unit tests pass
     - Container starts
     - Health check script succeeds

## Stage 5: Cucumber Test Setup
1. Setup Cucumber framework
   - Add Cucumber dependencies
   - Create feature file for number addition via Kafka
   - Define step definitions (pending implementation)
   - Verification: Cucumber structure compiles with pending steps

2. Setup in-memory Kafka test infrastructure
   - Add test containers for Kafka
   - Create test configuration
   - Verification: In-memory Kafka starts in test

## Stage 6: Kafka Integration and Business Logic
1. Add Kafka dependencies
   - Add Kafka client libraries
   - Verification: Dependencies resolve

2. Implement Kafka consumer
   - Create number consumer service
   - Add unit tests
   - Verification: Tests pass

3. Implement adder service
   - Create service to maintain running total
   - Add unit tests
   - Verification: Tests pass

4. Implement Kafka producer
   - Create service to emit running totals
   - Add unit tests
   - Verification: Tests pass

5. Complete Cucumber implementation
   - Implement pending step definitions
   - Connect to in-memory Kafka
   - Verification: All Cucumber scenarios pass

## Stage 7: Final Integration
1. Complete logging implementation
   - Add comprehensive logging
   - Verify log output
   - Verification: Logs appear as expected

2. Final verification
   - All tests pass
   - Coverage reports generated
   - Container builds
   - Health check works
   - Kafka integration verified
   - Cucumber scenarios pass 