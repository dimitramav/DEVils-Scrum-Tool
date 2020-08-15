# ScrumTool REST API

## Some facts about our API:
- No admin user has been assigned in the database, nor a front-end interface has been implemented for administrators (the endpoints just for administrators are included only for theory)
- Most of the objects that are being transfered are entities (with proper JSON Ignore annotations on some of their attributes, like most foreign keys). The rest are custom model objects (DTOs), created just for this purpose. All these Java objects are located in entities and models directories respectively.
- All successful HTTP Response Status are set to 200 (except from Notification DELETE operation that is set to return a 202 -Accepted- status, for testing purposes)
- Custom Error HTTP Responses are set to return a JSON payload with identifier "serverErrorMessage", followed by the respective error message
- Passwords are encrypted by BCrypt Encoder, while we use an Authorization Bearer Token
- CORS allows REST requests only from our client's server (e.g. `localhost:9000` on development mode)

## Our RESTful API Table

| URI Endpoint | HTTP Method | Resource (Java Class) | Description | Request Body Parameters (Java Class) | Request Query Parameters | Authorization Token | Response JSON (Java Class) |
|-|:-:|:-:|-|:-:|:-:|:-:|:-:|
| /hello | GET | HelloController | Dummy controller to test that server is working | - | - | No | String: "Greetings from Spring Boot!" |
| /authenticate | POST | AuthenticationController | Check user login credentials (email, password) and returns user's id, username and a json web token (jwt) | AuthenticationRequest | - | No | AuthenticationResponse |
| /users | GET | UserController | Retrieve all users from database (admin) | - | - | Yes (ADMIN) | Iterable User |
| /exists/username/{username} | GET | UserController | Check if the given username exists | - | - | No | boolean |
| /exists/username/{email} | GET | UserController | Check if the given email exists | - | - | No | boolean |
| /signup | POST | UserController | Create a new user (on signup) | User | - | No | User |
| /profile/{username} | GET | UserController | Get the user by username (use profile class as attributes like passwords are emitted for security reasons) | - | - | Yes | Profile |
| /profile/{username} | PUT | UserController | Update user's profile (by username) | Profile | - | Yes | Profile |
| /password/{userId}/matches | POST | UserController | Check if the attached password matches with the user with the given id | AuthenticationRequest | - | Yes | boolean |
| /password/{userId}/update | PUT | UserController | Update the user's (with id = userId) password (password stored inside AuthenticationRequest class) | AuthenticationRequest | - | Yes | String |
| /users/{userId} | DELETE | UserController | Delete this user (and all his/her data) from the database | - | - | Yes | - |
| /projects | GET | ProjectController | Retrieve all projects from database (admin) | - | - | Yes (ADMIN) | Iterable Project |
| /users/{userId}/numOfProjects | GET | ProjectController | Get the total number of active (or archived) projects | - | isDone (boolean) | Yes | Integer |
| /users/{userId}/projects | GET | ProjectController | Get a specific number of active (or archived) projects, depending on pagination | - | isDone (boolean), recordsPerPage (int), currentPage (int) | Yes | List Project |
| /users/{userId}/projects | POST | ProjectController | Create a new project | Project | - | Yes | Project |
| /users/{userId}/projects/{projectId} | GET | ProjectController | Get the project overview of the project with id = projectId (current (or specific) sprint's information, total sprint's tasks and issues) | - | sprintId (int-optional) | Yes | ProjectOverview |
| /users/{userId}/projects/{projectId} | PUT | ProjectController | Update the project's information | Project | - | Yes | Project |
| /users/{userId}/projects/{projectId}/changeIsDone | PUT | ProjectController | Change this project's state (from active to archived or vise versa) | Boolean | - | Yes | - |
| /users/{userId}/projects/{projectId} | DELETE | ProjectController | Delete this project (only Product Owner can delete a project) | - | - | Yes | - |
| /users/{userId}/projects/{projectId}/projectAuthorization | GET | ProjectController | Check if this user is a member of this project, so as to prevent unauthorized users from intervening to other projects (by url), call this endpoint from navbar component, every time that client's url has a project id | - | - | Yes | Boolean |
| /users/{userId}/projects/{projectId}/members | GET | TeamMemberController | Get all the members of this project | - | - | Yes | List TeamMember |
| /users/{userId}/projects/{projectId}/members | POST | TeamMemberController | Add a new member into the project's team, assigning upon him/her a role (e.g. Developer) | String | - | Yes | - |
| /users/{userId}/projects/{projectId}/members | DELETE | TeamMemberController | Remove this member from this project's team | - | - | Yes | - |
| /notifications | GET | NotificationController | Retrieve all notifications from database (admin) | - | - | Yes (ADMIN) | Iterable Notification |
| /users/{userId}/notifications | GET | NotificationController | Get all user's notifications | - | - | Yes | List Notification |
| /users/{userId}/notifications | POST | NotificationController | Create a new notification | Notification | - | Yes | - |
| /users/{userId}/notifications/{notificationId} | DELETE | NotificationController | Delete this notification | - | - | Yes | - |
| /sprints | GET | SprintController | Retrieve all sprints from database (admin) | - | - | Yes (ADMIN) | Iterable Sprint |
| /users/{userId}/projects/{projectId}/sprints | GET | SprintController | Get all project's sprints | - | - | Yes | List Sprint |
| /users/{userId}/projects/{projectId}/sprints | POST | SprintController | Create a new sprint for this project (as current) | Sprint | - | Yes | Sprint |
| /users/{userId}/projects/{projectId}/sprints/{sprintId} | GET | SprintController | Get this sprint (with id = sprintId) | - | - | Yes | Sprint |
| /users/{userId}/projects/{projectId}/sprints/{sprintId} | PUT | SprintController | Update this sprint's information | Sprint | - | Yes | Sprint |
| /users/{userId}/projects/{projectId}/sprints/{sprintId}/makeSprintCurrent | PUT | SprintController | Tag this sprint as current (e.g. a past sprint that needs to be attached once again) | Sprint | - | Yes | Sprint |
| /users/{userId}/projects/{projectId}/sprints/{sprintId} | DELETE | SprintController | Delete this sprint from project | - | - | Yes | - |
| /pbis | GET | PBIController | Retrieve all pbis (product backlog items, aka epics and user stories) from database (admin) | - | - | Yes (ADMIN) | Iterable PBI |
| /users/{userId}/projects/{projectId}/pbis | GET | PBIController | Get the epics or the user stories of this project (backlog) | - | isEpic (boolean), epicId (int-optional) | Yes | List PBI |
| /users/{userId}/projects/{projectId}/pbis | POST | PBIController | Create a new pbi for this project's backlog | PBI | - | Yes | PBI |
| /users/{userId}/projects/{projectId}/pbis | PUT | PBIController | Update the attached pbi (id need to be included on pbi body item) | PBI | - | Yes | PBI |
| /users/{userId}/projects/{projectId}/pbis/sprintUpdate | PUT | PBIController | Add a list of stories to a sprint (sprint id included on body items) or remove these stories from this particular sprint | List SprintStory | - | Yes | - |
| /users/{userId}/projects/{projectId}/pbis/{pbiId} | DELETE | PBIController | Delete this product backlog item | - | - | Yes | - |
| /users/{userId}/projects/{projectId}/sprintStories | GET | PBIController | Get all the stories that are attached to the sprint with id equal to the one in query params | - | sprintId (int) | Yes | List PBI |
| /tasks | GET | TaskController | Retrieve all tasks from database (admin) | - | - | Yes (ADMIN) | Iterable Task |
| /users/{userId}/projects/{projectId}/tasks | GET | TaskController | Get all the tasks that are attached to the sprint with id equal to the one in query params | - | sprintId (int) | Yes | List TaskDTO |
| /users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks | POST | TaskController | Create a new task for this pbi (user story) | Task | - | Yes | TaskDTO |
| /users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks | PUT | TaskController | Update the attached task's information (id need to be included on task body item) | Task | - | Yes | TaskDTO |
| /users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks/{taskId} | DELETE | TaskController | Delete this task | - | - | Yes | - |
| /issues | GET | IssueController | Retrieve all issues from database (admin) | - | - | Yes (ADMIN) | Iterable Issue |
| /users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks/{taskId}/issues | GET | IssueController | Get all issues of this task (by taskId) | - | - | Yes | List Issue |
| /users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks/{taskId}/issues | POST | IssueController | Create a new issue for this task | Issue | - | Yes | Issue |
| /users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks/{taskId}/issues | PUT | IssueController | Update the attached issue's information (id need to be included on issue body item) | Issue | - | Yes | Issue |
| /users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks/{taskId}/issues/{issueId} | DELETE | IssueController | Delete this issue | - | - | Yes | - |
