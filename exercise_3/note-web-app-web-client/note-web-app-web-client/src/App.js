import './App.css';
import React from 'react';
import { Provider } from 'react-redux';
import store from './stores/store';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import SignIn from './pages/SignIn';
import SignUp from './pages/SignUp';
import Home from './pages/Home';
import ProtectedRoute from './pages/ProtectedRoute';


function App() {

  return <Provider store={store}>
    <BrowserRouter>
      <Routes>
        <Route index path="/" element={
          <ProtectedRoute>
            <Home />
          </ProtectedRoute>
        } />
        <Route path="/login" element={<SignIn />} />
        <Route path="/register" element={<SignUp />} />
      </Routes>
    </BrowserRouter>
  </Provider>
}

export default App;
