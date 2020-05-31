import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import Blog from './components/Home/Blog';
import SignIn from './components/SignIn/SignInSide';
import SignUp from './components/SignUp/SignUp';
import Post from './components/Post/Post';

const App = () => (
    <Router>
        <Route path = "/" exact  component = {Blog} />
        <Route path = "/signin" exact component = {SignIn}/>
        <Route path = "/signup" exact component = {SignUp}/>
        <Route path = "/post" exact component = {Post}/>
    </Router>
);

export default App;