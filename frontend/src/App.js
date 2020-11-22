import logo from './logo.svg';
import './App.css';
import UserSignupPage from './pages/UserSignupPage';
import * as apiCalls from './api/apiCalls';

const actions = {
  postSignup: apiCalls.signup,
};

function App() {
  return (
    <div>
      <UserSignupPage actions={actions} />
    </div>
  );
}

export default App;
