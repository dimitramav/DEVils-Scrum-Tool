# DEVils-Scrum-Tool Web Pages URL Table
This table contains all the pages that this platform contains, along with their URLs, the Vue components that represent them, if they are working with a logged in user (or not) and a brief description.

## URL Table

| URL | Component (Vue.js) | Authorized User | Description |
|-|:-:|:-:|-|
| / | IndexDefault | No | The default Index page of application, when the user is not logged in. Basic information about the app are provided here. |
| /signup | SignUp | No | A from with signup credentials lies in this page. When user has fill them and press "Sign Up", he/she is redirected to Index page as a logged in user. |
| /signin | SignIn | No | Log In page, where user fill in his/her email and password to insert into the application. |
| /forgotpass | ForgotPass | No | If the user has forgotten the password, he is supposed to retrieve it by a link to his/her email. This function is NOT IMPLEMENTED! |
| / | Index | Yes | The index page of DEVils-Scrum-Tool when the user is logged in. All the projects that user participates in are presented here, along with the ability to create new ones. |
| /users/{username} | Profile | Yes | A card with basic profile information about the user with attached (in URL) username. This can be either the logged in user's profile, or some other one's. |
| /editprofile | EditProfile | Yes | User can edit his profile information, change his password or delete his account. |
| /project/{projectId}/overview | ProjectPageOverview | Yes | The overview of the selected project (with id = projectId), showing the project's info, its current sprint's information, along with the team members. |
| /project/{projectId}/backlog | Backlog | Yes | The product backlog page. A user can create/edit/delete epics and user stories in this page. |
| /project/{projectId}/sprints | OldSprints | Yes | A list with all sprints of this project lies in this page. |
| /project/{projectId}/newsprint | NewSprint | Yes | A form to create a new current sprint. Along with sprint's information, a user can add user stories in this sprint before its creation. |
| /project/{projectId}/sprintbacklog/{sprintId} | SprintBacklog | Yes | The core of this application, with a big kanban board, in which user can add/edit/move/delete tasks and issues that are attached to a user story. Also he/she can add user stories in the sprint backlog, and update the sprint's information. |
| /unauthorized | Unauthorized | - | If a user attempts (with a hardcoded URL) to access a page where he/she has no right to (e.g a non-logged in user to a user's profile, or an authorized one to a project which has no access to, then the application redirects in this page by default. |
| * | PageNotFound | - | Whenever an invalid URL is provided (anything else than the above URLs), the application redirects to this default page. |
