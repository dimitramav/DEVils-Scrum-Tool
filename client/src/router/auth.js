// headers for axios calls
var config = {
    headers: {
        auth: localStorage.getItem('auth_token'),
        'Content-Type': 'application/json',
    },
}
export default config
