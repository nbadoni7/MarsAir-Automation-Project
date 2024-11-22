package com.marsairlines.runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",  // Path to feature files
        glue = {
                "com.marsairlines.hooks", // Hooks package
                "com.marsairlines.StepDefinitions" // Step definitions package
        },  // Path to step definitions
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        monochrome = true,
        tags = " @runTest"  // Specify the tag(s) to filter scenarios
)
public class TestRunner extends AbstractTestNGCucumberTests {

        @Override
        @DataProvider(parallel = false)  // Enable parallel execution if needed
        public Object[][] scenarios() {
                return super.scenarios();
        }
}
