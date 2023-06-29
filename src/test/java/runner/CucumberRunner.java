package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = { "stepdefs", "hooks"},
        tags = "(@smoke or @regression) and (not @slow)"
)
public class CucumberRunner {
}