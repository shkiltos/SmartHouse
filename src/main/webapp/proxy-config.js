const PROXY_CONFIG = [
    {
      context: [
        "/api",
        "/login",
        "/logout",
        "/api/user",
        "/api/devices"
      ],
      secure: false,
      target: {
        "host": "localhost",
        "protocol": "http:",
        "port": 8080
      },
      logLevel: "debug"
    }
  ];
  
  module.exports = PROXY_CONFIG;
  