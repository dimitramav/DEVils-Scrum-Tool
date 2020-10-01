// Express server for heroku deployment
const express = require('express')
const port = process.env.PORT || 9000
const app = express()

app.use(express.static(__dirname + '/dist/'))
app.get(/.*/, function (req, res) {
    res.sendFile(__dirname + '/dist/index.html')
})
app.listen(port)

console.log('Server started on port: ', port)

// Deploy on heroku through git from root directory, check:
// https://stackoverflow.com/questions/26241683/heroku-deploy-a-sub-directory
