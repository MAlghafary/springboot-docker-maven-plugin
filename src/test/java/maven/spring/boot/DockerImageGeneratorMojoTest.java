package maven.spring.boot;

import static org.junit.Assert.assertNotNull;

import org.apache.maven.plugin.testing.MojoRule;
import org.codehaus.plexus.PlexusTestCase;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

public class DockerImageGeneratorMojoTest {

    @Rule
    public MojoRule rule = new MojoRule();

    @Test
    public void testDockerImageMojoExecutes() throws Exception {
        File testPom = new File(PlexusTestCase.getBasedir(), "src/test/resources/");
        DockerImageGeneratorMojo dockerImageGeneratorMojo =
            (DockerImageGeneratorMojo) rule.lookupConfiguredMojo(testPom.getAbsoluteFile(), "docker-image");
        assertNotNull(dockerImageGeneratorMojo);
        dockerImageGeneratorMojo.execute();
    }

}