const fs = require("fs");
const express = require("express");

const app = express();
const port = 5500;

const data = JSON.parse(fs.readFileSync("data.json"));

app.listen(port);

app.get("/", (req, res)=>{
    const query = req.query;
    if(query.id) res.send(data[query.id]);
    else res.send(data);
});

app.post("/", (req, res)=>{
    const body = req.body;
    // if(query.id) res.send(data[query.id]);
    console.log(req.body.id);
    res.send("done!")
});
