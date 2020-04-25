
module.exports = {
    // Proxy does not work for axios (bug)
    devServer: {
        /*proxy: {
            '^/api/': {
                target: 'http://localhost:8765',
                pathRewrite: { '^/api/': '/api/' },
                ws: true,
                changeOrigin: true,
                logLevel: 'debug',
            }
        },*/
        //https: true,
        port: 9000
    }
}
