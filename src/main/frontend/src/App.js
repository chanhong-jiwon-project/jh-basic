import { Route, Routes } from 'react-router-dom';
import Login from './login/Login';
import Register from './login/Register';

const App = () => {
  return (
    <Routes>
        <Route path="/" element={<Login/>} />
        <Route path="/register" element={<Register/>} />
    </Routes>
  );
};

export default App;
