## Getting Started
Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code. The **Integration Robomap Cloud** project is basically built in **VS Code Java**.

## Start a Project
- Download Java Coding Pack from [Java Coding Pack](https://code.visualstudio.com/docs/java/java-tutorial).
- Install the package. 
- Run `>java create` to create Java Project with No Build Tools.
- Select the project location.
- Input the name of the project.


## Build

## 

## Folder Structure
The workspace contains two folders by default, where:
- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management
The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

# Build
`docker build -t your-app-image .`

# Run
`docker run -p 8080:8080 --name your-app-container your-app-image`

# Push into Docker registry
`docker tag your-app-image username/your-app:latest`
`docker push username/your-app:latest`
