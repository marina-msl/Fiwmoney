import './App.css';
import Header from './Components/Header'
import Stocks from './Components/Stocks'

function App() {
  return (
    <div className="app-container">
      <Header/>
      <main>
          <Stocks />
      </main>
     </div>  
  );
}

export default App;
