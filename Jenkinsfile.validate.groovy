pipeline {
  agent any

  environment {
    TF_IN_AUTOMATION = "true"
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Terraform Format Check') {
      steps {
        sh 'terraform fmt -check -recursive'
      }
    }

    stage('Terraform Init') {
      steps {
        sh 'terraform init'
      }
    }

    stage('Terraform Validate') {
      steps {
        sh 'terraform validate'
      }
    }

    stage('Terraform Plan (Optional)') {
      steps {
        sh 'terraform plan -no-color || true'
      }
    }
  }

  post {
    failure {
      echo "Validation failed. PR cannot be merged."
    }
    success {
      echo "Validation successful. PR can be merged."
    }
  }
}
