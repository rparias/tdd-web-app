import logo from './logo.svg';
import './App.css';
import LoginPage from './pages/LoginPage';
import * as apiCalls from './api/apiCalls';

const actions = {
  postSignup: apiCalls.signup,
};

function App() {
  return (
    <div>
      <LoginPage />
    </div>
  );
}

export default App;
