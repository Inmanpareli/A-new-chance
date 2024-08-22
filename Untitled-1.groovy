.
├── app.js
├── package.json
├── routes
│   ├── index.js
│   ├── call.js
│   └── feedback.js
└── views
    ├── index.ejs
        └── dashboard.ejs{
              "name": "inmate-support-app",
                "version": "1.0.0",
                  "description": "App to route calls to professionals and collect feedback.",
                    "main": "app.js",
                      "dependencies": {
                          "express": "^4.17.1",
                              "body-parser": "^1.19.0",
                                  "ejs": "^3.1.6"
                                    },
                                      "scripts": {
                                          "start": "node app.js"
                                            },
                                              "author": "Inman Pareli",
                                                "license": "MIT"
                                                }
        }const express = require('express');
        const bodyParser = require('body-parser');
        const app = express();
        const port = process.env.PORT || 3000;

        app.set('view engine', 'ejs');
        app.use(bodyParser.urlencoded({ extended: true }));
        app.use(express.static('public'));

        // Routes
        const indexRoute = require('./routes/index');
        const callRoute = require('./routes/call');
        const feedbackRoute = require('./routes/feedback');

        app.use('/', indexRoute);
        app.use('/call', callRoute);
        app.use('/feedback', feedbackRoute);

        app.listen(port, () => {
            console.log(`App running on http://localhost:${port}`);
            });const express = require('express');
            const router = express.Router();

            router.get('/', (req, res) => {
                res.render('index', { title: "Inmate Support App" });
                });

                module.exports = router;const express = require('express');
                const router = express.Router();

                // Mock data: professionals' status
                let professionals = [
                    { id: 1, name: "John Doe", available: true },
                        { id: 2, name: "Jane Smith", available: false },
                        ];

                        // Get the list of available professionals
                        router.get('/', (req, res) => {
                            res.render('dashboard', { professionals });
                            });

                            // Update availability (in a real app, this would be more complex)
                            router.post('/update', (req, res) => {
                                const { id, available } = req.body;
                                    professionals = professionals.map(professional =>
                                            professional.id == id ? { ...professional, available: available === 'true' } : professional
                                                );
                                                    res.redirect('/call');
                                                    });

                                                    module.exports = router;const express = require('express');
                                                    const router = express.Router();

                                                    let feedbacks = [];

                                                    // Display feedback form
                                                    router.get('/', (req, res) => {
                                                        res.render('feedback', { feedbacks });
                                                        });

                                                        // Submit feedback
                                                        router.post('/submit', (req, res) => {
                                                            const { professionalId, rating, comments } = req.body;
                                                                feedbacks.push({ professionalId, rating, comments });
                                                                    res.redirect('/feedback');
                                                                    });

                                                                    module.exports = router;<!DOCTYPE html>
                                                                    <html>
                                                                    <head>
                                                                        <title><%= title %></title>
                                                                        </head>
                                                                        <body>
                                                                            <h1>Welcome to the Inmate Support App</h1>
                                                                                <a href="/call">View Professionals</a>
                                                                                    <a href="/feedback">Submit Feedback</a>
                                                                                    </body>
                                                                                    </html><!DOCTYPE html>
                                                                                    <html>
                                                                                    <head>
                                                                                        <title>Dashboard</title>
                                                                                        </head>
                                                                                        <body>
                                                                                            <h1>Professionals</h1>
                                                                                                <ul>
                                                                                                        <% professionals.forEach(professional => { %>
                                                                                                                    <li>
                                                                                                                                    <%= professional.name %> - 
                                                                                                                                                    <%= professional.available ? "Available" : "Unavailable" %>
                                                                                                                                                                    <form action="/call/update" method="POST" style="display:inline;">
                                                                                                                                                                                        <input type="hidden" name="id" value="<%= professional.id %>">
                                                                                                                                                                                                            <select name="available" onchange="this.form.submit()">
                                                                                                                                                                                                                                    <option value="true" <%= professional.available ? "selected" : "" %>>Available</option>
                                                                                                                                                                                                                                                            <option value="false" <%= !professional.available ? "selected" : "" %>>Unavailable</option>
                                                                                                                                                                                                                                                                                </select>
                                                                                                                                                                                                                                                                                                </form>
                                                                                                                                                                                                                                                                                                            </li>
                                                                                                                                                                                                                                                                                                                    <% }); %>
                                                                                                                                                                                                                                                                                                                        </ul>
                                                                                                                                                                                                                                                                                                                            <a href="/">Back to Home</a>
                                                                                                                                                                                                                                                                                                                            </body>
                                                                                                                                                                                                                                                                                                                            </html><!DOCTYPE html>
                                                                                                                                                                                                                                                                                                                            <html>
                                                                                                                                                                                                                                                                                                                            <head>
                                                                                                                                                                                                                                                                                                                                <title>Feedback</title>
                                                                                                                                                                                                                                                                                                                                </head>
                                                                                                                                                                                                                                                                                                                                <body>
                                                                                                                                                                                                                                                                                                                                    <h1>Submit Feedback</h1>
                                                                                                                                                                                                                                                                                                                                        <form action="/feedback/submit" method="POST">
                                                                                                                                                                                                                                                                                                                                                <label for="professionalId">Professional ID:</label>
                                                                                                                                                                                                                                                                                                                                                        <input type="text" id="professionalId" name="professionalId" required><br><br>
                                                                                                                                                                                                                                                                                                                                                                <label for="rating">Rating:</label>
                                                                                                                                                                                                                                                                                                                                                                        <input type="number" id="rating" name="rating" min="1" max="5" required><br><br>
                                                                                                                                                                                                                                                                                                                                                                                <label for="comments">Comments:</label><br>
                                                                                                                                                                                                                                                                                                                                                                                        <textarea id="comments" name="comments" required></textarea><br><br>
                                                                                                                                                                                                                                                                                                                                                                                                <button type="submit">Submit</button>
                                                                                                                                                                                                                                                                                                                                                                                                    </form>
                                                                                                                                                                                                                                                                                                                                                                                                        <h2>Past Feedback</h2>
                                                                                                                                                                                                                                                                                                                                                                                                            <ul>
                                                                                                                                                                                                                                                                                                                                                                                                                    <% feedbacks.forEach(feedback => { %>
                                                                                                                                                                                                                                                                                                                                                                                                                                <li>Professional ID: <%= feedback.professionalId %> - Rating: <%= feedback.rating %>/5<br>Comments: <%= feedback.comments %></li>
                                                                                                                                                                                                                                                                                                                                                                                                                                        <% }); %>
                                                                                                                                                                                                                                                                                                                                                                                                                                            </ul>
                                                                                                                                                                                                                                                                                                                                                                                                                                                <a href="/">Back to Home</a>
                                                                                                                                                                                                                                                                                                                                                                                                                                                </body>
                                                                                                                                                                                                                                                                                                                                                                                                                                                </html> 
                                                                                                                                                                                                                                                                                                                                                                                                                                    <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Site Title</title>
</head>
<body>
    <h1>Welcome to My Site</h1>
    <p>This is the main page.</p>
</body>
</html>
                                                                                                                                                                                                                                                                                                                        