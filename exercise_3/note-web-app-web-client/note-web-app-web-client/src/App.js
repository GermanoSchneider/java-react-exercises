import './App.css';
import React from 'react';
import { Provider } from 'react-redux';
import store from './stores/store';
import SignUp from './pages/SignUp';


function App() {

  return <Provider store={store}>
      <SignUp />
  </Provider>
}

export default App;
