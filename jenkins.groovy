pipeline {
    agent any
    stages {

        stage('Docker Cleanup') {
            steps {
                script {
                    def containerName = "My-first-containe211"
                    def imageName = "tuxwow/my-java-app:latest"

                    // Try to stop the container
                    try {
                        sh "sudo docker stop ${containerName}"
                    } catch (Exception e) {
                        echo "Error stopping container: ${e.getMessage()}"
                    }

                    // Try to remove the container
                    try {
                        sh "sudo docker rm ${containerName}"
                    } catch (Exception e) {
                        echo "Error removing container: ${e.getMessage()}"
                    }

                    // Try to remove the image
                    try {
                        sh "sudo docker rmi ${imageName}"
                    } catch (Exception e) {
                        echo "Error removing image: ${e.getMessage()}"
                    }

                    // Regardless of errors, proceed to the next stage
                    echo "Docker cleanup completed (with possible errors skipped)."
                        }
                    }
        }
 
        stage('checkout') {
            steps {
                git url: 'https://github.com/tuxwow/Docker_Jenkins_Pipeline', branch: "main"
            }
        }
        stage('Build Image') {
            steps {
                sh 'sudo docker build -t tuxwow/my-java-app:1.0 .'
                sh 'sudo docker tag tuxwow/my-java-app:1.0 tuxwow/my-java-app:latest'
            }
        }
        stage('Docker login') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockercred', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                    sh "echo $PASS | sudo docker login -u $USER --password-stdin"
                    sh 'sudo docker push tuxwow/my-java-app:latest'
                }
            }
        }
        stage('Deploy') {
            steps {
                    sh 'sudo docker run -itd --name My-first-containe211 -p 8084:8084 tuxwow/my-java-app:latest'
                
            }
        }
    }
}
