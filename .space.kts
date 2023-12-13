job("Build and Deploy") {
    container(image = "docker") {
        env["DOCKER_USERNAME"] = "{{ project:DOCKER_USERNAME }}"
        env["DOCKER_PASSWORD"] = "{{ project:DOCKER_PASSWORD }}"
        shellScript {
            content = """
                # 构建 Docker 镜像 
                docker build -t kargobangapp:latest .
                
                # 推送镜像到 Docker 仓库
                echo "${'$'}DOCKER_PASSWORD" | docker login -u "${'$'}DOCKER_USERNAME" --password-stdin
                docker tag kargobangapp:latest mrowenovo/kargobang:latest
                docker push mrowenovo/kargobang:latest

                # 使用 docker-compose 启动生产环境容器
                docker-compose -f compose.prod.yaml up -d
            """
        }
    }

}
