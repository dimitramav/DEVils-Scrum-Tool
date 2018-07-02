# DEVils-Scrum-Tool
Web cooperative platform for software management. This platform helps teams to implement software projects based on agile methodology (SCRUM) via sprints, epics, user stories and issues.

## Build Project
    cd ScrumTool
    gradle build

## Run Project

### Run Back-end
    gradle appRun
  
### Run Front-end
    gradle frontendRun
> To successfully run front-end you must have the latest versions of npm and nodeJS

### Add security exception for SSL certificate
	https://localhost:8443/app/api/users/1/projects (enable backend certificate)
	https://localhost:8080/#/ 						(frontend)