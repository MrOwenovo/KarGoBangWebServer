job("Qodana") {
  startOn {
    gitPush {
      anyBranchMatching {
        +"main"
      }
    }
    codeReviewOpened{}
  }
  container("jetbrains/qodana-jvm") {
    env["QODANA_TOKEN"] = "{{ project:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJvcmdhbml6YXRpb24iOiJ6ODY0diIsInByb2plY3QiOiIzSjdrWSIsInRva2VuIjoiQWdlcXEifQ.4yZkBQI0BBAipawTj_8z3tsi07NiVw8OkAKSuQaPHzA }}"
    shellScript {
      content = "qodana"
    }
  }
}

job("Build and Deploy") {
    container(image = "docker") {
        env["DOCKER_USERNAME"] = "{{ env.DOCKER_USERNAME }}"
        env["DOCKER_PASSWORD"] = "{{ env.DOCKER_PASSWORD }}"
        shellScript {
            content = """
                # 构建 Docker 镜像 
                docker build -t kargobangapp:latest .
                
              
                # 推送镜像到 Docker 仓库
                # 假设您已在项目的环境变量中配置了 DOCKER_USERNAME  DOCKER_PASSWORD
                echo "${'$'}DOCKER_PASSWORD" | docker login -u "${'$'}DOCKER_USERNAME" --password-stdin
                docker tag kargobangapp:latest mrowenovo/kargobang:latest
                docker push mrowenovo/kargobang:latest

                # 使用 docker-compose 启动生产环境容器
                # 假设您的 compose.prod.yaml 在项目根目录
                docker-compose -f compose.prod.yaml up -d
            """
        }
    }
    startOn {
        gitPush {
            anyBranchMatching {
                +"refs/heads/main"
            }
        }
    }
}
