#!groovy​

 properties([
   parameters([
//         string(name: 'branchName', defaultValue: 'dev', description: 'Branch name'),
         booleanParam (defaultValue: false, name: 'deployCustomer', description: 'Check to deploy customer service'),
         booleanParam (defaultValue: false, name: 'deployFraud',  description: 'check to deploy fraud service'),
         booleanParam (defaultValue: false, name: 'deployNotification', description: 'check to deploy notification service'),
   ])
 ])

pipeline {
    agent any



    stages {
        stage('Git-Clone') {

            steps {
                checkout([$class: 'GitSCM',
                branches: [[name: '*/develop']],
                userRemoteConfigs: [[url: 'https://github.com/OD-Oraf/odservices.git']]])
            }
        }
        stage('Set-Kubeconfig') {
            steps {
                sh 'export KUBECONFIG=/home/odservices-kubeconfig.yaml'
            }

        }


        stage('Inject-Secrets') {
            steps {
                withCredentials([string(credentialsId: 'db-pass', variable: 'postgres_pass')]) {
                    sh "sed 's/DB_PASSWORD/${postgres_pass}/' k8s/services/customer/deployment.yml"
                    sh "sed 's/DB_PASSWORD/${postgres_pass}/' k8s/services/fraud/deployment.yml"
                    sh "sed 's/DB_PASSWORD/${postgres_pass}/' k8s/services/notification/deployment.yml"
                }
            }
        }

        // stage('Build-All-Images') {
        //     steps {
        //         sh 'mvn clean package -P build-docker-image'
        //     }
        // }

        stage('Deploy-Customer-Service') {
            when {
                expression {
                    "${deployCustomer}" == true;
                }
            }
            steps {
                sh "cd customer/ && mvn package -P build-docker-image"
                sh 'kubectl apply -f k8s/services/customer'
            }
        }

        stage('Deploy-Fraud-Service') {
            when {
                expression {
                    "${deployFraud}" == true;
                }
            }
            steps {
                sh "cd fraud/ && mvn package -P build-docker-image"
                sh 'kubectl apply -f k8s/services/fraud'
            }
        }

        stage('Deploy-Notification-Service') {
            when {
                expression {
                    "${deployNotification}" == true;
                }
            }
            steps {
                sh "cd notification/ && mvn package -P build-docker-image"
                sh 'kubectl apply -f k8s/services/notification'
            }
        }
    }

    post {
        // Clean after build
        always {
            cleanWs(cleanWhenNotBuilt: false,
                    deleteDirs: true,
                    disableDeferredWipeout: true,
                    notFailBuild: true,
                    patterns: [[pattern: '.gitignore', type: 'INCLUDE'],
                              [pattern: '.propsfile', type: 'EXCLUDE']])
        }
    }
}