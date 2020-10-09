package org.example;

import org.example.dto.RepoList;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Arrays.setAll;

public class App {

    private final static String MAVEN_HOME = "/usr/share/maven";

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.run(args);
    }

    public void run(String... args) throws Exception {
        BitBucketClient bitBucketClient = new BitBucketClient();

        final String rootFolderPath = "/home/roman/GODEL_WORK/";
        final List<String> projects =
                asList("CL", "COT", "DB", "EI", "EIN", "EL", "ESD", "EM", "EWR", "LOGNET", "LWSF", "NEX", "PREP", "PUPPET", "QA",
                        "REACH_COMMON", "REACH_CUST_JRNY", "REACH_CUST_REG", "REACH_SS", "RME", "RR", "RTOOLS", "SPC", "SPE", "SP", "SPSE",
                        "REACH_ELEC", "REACH_GAS");
        projects.forEach(projectName -> {
            final File projectRootFolder = new File(rootFolderPath + projectName);
            projectRootFolder.mkdir();

            final RepoList repoList = bitBucketClient.getBitBucketRepoList(projectName);
            repoList.getValues().forEach(repo -> {
                final File repoFolder = new File(projectRootFolder.getAbsolutePath() + "/" + repo.getName());
                repoFolder.mkdir();

                try {
                    if (repoFolder.list().length == 0) {
                        final String sshUrl = repo.getLinks()
                                .getClone()
                                .stream()
                                .filter(clone -> clone.getName().equals("http"))
                                .findFirst()
                                .get()
                                .getHref();
                        runSshGitClone(sshUrl, repoFolder.getAbsolutePath());
                    }
                    runLoadingOnMvnDependencies(repoFolder);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        System.out.println("All repos successfully cloned.");
    }

    private void runSshGitClone(final String sshUrl, final String absolutePath) throws IOException, InterruptedException {
        System.out.println(LocalDateTime.now() + ": Started git clone of " + sshUrl);
        Process process = Runtime.getRuntime().
                exec("git clone " + sshUrl + " " + absolutePath);
        process.waitFor();
        System.out.println(LocalDateTime.now() + ": Finished git clone");
    }

    private void runLoadingOnMvnDependencies(final File targetFolder) throws IOException, InterruptedException {
        System.out.println(
                LocalDateTime.now() + ": Started mvn loading of dependencies " + targetFolder.getAbsolutePath());
        Process process = Runtime.getRuntime().
                exec(MAVEN_HOME + "/bin/mvn dependency:go-offline", null, targetFolder);

        String line;
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ((line = input.readLine()) != null) {
            System.out.println("!!!" + line);
        }
        input.close();
        process.waitFor();
        System.out.println(LocalDateTime.now() + ": Finished mvn loading of dependencies");
    }
}
