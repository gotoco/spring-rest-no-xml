// Place third party dependencies in the lib folder
//
// Configure loading modules from the lib directory,
// except 'app' ones,
requirejs.config({

    "paths": {
        jquery:"./lib/jquery-1.11.1.min"
    }
});

// Load the main app module to start the app
requirejs(["./app/main"]);