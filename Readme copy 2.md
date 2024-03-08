# Build
`docker build -t your-app-image .`

# Run
`docker run -p 8080:8080 --name your-app-container your-app-image`

# Push into Docker registry
`docker tag your-app-image username/your-app:latest`
`docker push username/your-app:latest`
