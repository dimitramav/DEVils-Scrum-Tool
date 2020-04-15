# DEVils-Scrum-Tool
Web cooperative platform for software management. This platform helps teams to implement software projects based on agile methodology (SCRUM) via sprints, epics, user stories and issues.

## Environment
This web application is created using Java, MySQL, Jetty server and Gradle for build in the backend, while the frontend
is implemented on Vue framework. For dependencies, check the respective README.md files in both files.

## Build and Run Back-End
- In back-end directory, follow the instructions given on [Back-End ReadMe](./back-end/README.md) in order to install
the database, build and run the jetty server and handle the ssl certificate in your browser

## Build and Run Front-End
- Just as above, go to front-end file, open a new bash instance and run `npm install` to install dependencies and then
`npm run serve` to run the front end. Read the [Front-End ReadMe](./front-end/README.md) for more.


## Add security exception for SSL certificate
	https://localhost:8443/             (enable backend certificate)
	https://localhost:9000/ 			(frontend)
> May require Log Out and then Log In when first run frontend url
