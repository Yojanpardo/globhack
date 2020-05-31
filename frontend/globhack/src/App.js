import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import Blog from './components/Home/Blog';
import SignIn from './components/SignIn/SignInSide';

const App = () => (
    <Router>
        <Route path = "/" exact  component = {Blog} />
        <Route path = "/signin" exact component = {SignIn}/>
    </Router>
);

export default App;