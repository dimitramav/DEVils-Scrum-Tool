# DEVils-Scrum-Tool

## Users

- Product Owner/Customer
- Scrum master
- Developer
- Administrator
- Not defined user



## Project Backlog

### Functional Requirements

| # | User Story Title              | User Story Description                                                              | User Type                                       | Story Specifications                                                                                                                                                                                                              | Priority | Non-functional Requirements |
|---|-------------------------------|-------------------------------------------------------------------------------------|-------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------|-----------------------------|
| 1 | Sign up                       | User needs to sign up to create/ participate in a project                           | Product owner/Customer, Scrum master, Developer | Create a form that contains the following fields: username(*), first name(*), last name(*), password(*), mail(*). Optionally user fills out Country, Job and Company (appearing as collapsed fields). Redirects to user homepage. | High     |                             |
| 2 | Sign in                       | User needs to sign in to create/ participate in a project                           | All users                                       | Create a form that contains the following fields: username , password. (μη λειτουργική απαιτηση forgot password). Redirects to user homepage.                                                                                     | High     |                             |
| 3 | Sign out                      | User needs to exit the application                                                  | All users                                       | Redirect the user to homepage after logging out                                                                                                                                                                                   | High     |                             |
| 4 | Accept invitation to project  | User is notified when a pending invitation occurs and he accepts it or declines it. | All users except admin                          | A badge (see bootstrap) appears in the top-right corner when a new notification occurs in a notification dropdown box.                                                                                                            | High     |                             |
| 5 | Select from multiple projects | User could have any role in many projects and selects one of them                   | All users except admin                          | Show a grid (see bootstrap) with all projects he participates                                                                                                                                                                     | High     |                             |

### Non-Functional Requirements

| User Story Title | User Story Description | User Type | Story Specifications | Priority | 
| -----------------|------------------------|-----------|----------------------|----------|
| Monday           |                        |           |                      |          |
