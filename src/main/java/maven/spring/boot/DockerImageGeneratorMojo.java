package maven.spring.boot;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * A mojo which generates a docker image for spring boot applications .
 */
@Mojo(name = "docker-image", defaultPhase = LifecyclePhase.PACKAGE)
public class DockerImageGeneratorMojo extends AbstractMojo {

    /**
     * The directory where the docker image will be generated.
     */
    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
    private File outputDirectory;

    @Parameter(defaultValue = "${project.artifactId}", property = "name")
    private String name;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        getLog().info("DockerImageGeneratorMojo ..... ");
        getLog().info(outputDirectory.getAbsolutePath());
        if (!targetJarAvailable()) {
            getLog().info("Target Jar not availanle ....");
            return;
        }
        try {
            File dockerFile = generateDockerFile();
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage());
        }
    }

    private boolean targetJarAvailable() {
        String jarFileName = project.getArtifactId() + "-" + project.getVersion() + ".jar";
        String jarFileFullPath = project.getBuild().getDirectory() + "/" + jarFileName;
        getLog().info("JarFileFullPath : " + jarFileFullPath);
        return FileUtils.fileExists(jarFileFullPath);
    }

    private File generateDockerFile() throws IOException {
        File dockerFile = new File(project.getBuild().getDirectory() + "/" + "dockerfile");
        FileUtils.fileWrite(dockerFile.getAbsolutePath(), "FROM openjdk:8-jdk-alpine");
        return dockerFile;
    }
}
