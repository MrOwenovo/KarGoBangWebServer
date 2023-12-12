job("Build") {
    container(image = "maven:3.6.3-jdk-11") {
        shellScript {
            content = """
            mvn clean install
            """
        }
    }
    triggers {
        gitPush {
            branchFilter {
                +":refs/heads/main"
            }
        }
    }
}
job("Test") {
    container(image = "maven:3.6.3-jdk-11") {
        shellScript {
            content = """
            mvn test
            """
        }
    }
    triggers {
        gitPush {
            branchFilter {
                +":refs/heads/main"
            }
        }
    }
}
job("Code Quality Check") {
    container(image = "sonarqube:latest") {
        shellScript {
            content = """
            mvn sonar:sonar \
                -Dsonar.projectKey=my_project_key \
                -Dsonar.host.url=http://my_sonar_server \
                -Dsonar.login=my_sonar_login
            """
        }
    }
    triggers {
        gitPush {
            branchFilter {
                +":refs/heads/main"
            }
        }
    }
}

