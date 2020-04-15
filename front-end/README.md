# Front End Implementation
The front end of DEVils-Scrum-Tool is created with Vue, so it requires an installation of nodejs.
The project was developed using Vue CLI (@vue/cli). Open a new bash instance to run front end:

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

#### Possible problems on npm serve
It is possible, when runnning `npm run serve`, an error with code `ELIFECYCLE` emits. In this case,
you can solve it by running the code below, according to https://stackoverflow.com/questions/49008498/npm-run-serve-error

    echo fs.inotify.max_user_watches=524288 | sudo tee -a /etc/sysctl.conf && sudo sysctl -p

### Add security exception for SSL certificate
	https://localhost:9000/#/
> May require Log Out (up-right on navbar) when first run frontend url (bug)


### More configuration (optional)
#### Compiles and minifies for production (optional)
```
npm run build
```

#### Run your unit tests
```
npm run test:unit
```

#### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
