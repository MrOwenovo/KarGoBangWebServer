job("Build Docker Image") {
    container("docker:19.03.12") {
        shellScript {
            content = """
            docker build -t my-springboot-app .
            """
        }
    }
    
}

job("Build") {
    container(image = "maven:3.6.3-jdk-11") {
        shellScript {
            content = """
            mvn clean install
            """
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

}

