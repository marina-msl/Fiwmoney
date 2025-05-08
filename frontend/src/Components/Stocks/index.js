import { useRef, useState } from 'react'
import './style.css'
import { stock } from './dataStock'


function Stocks() {
    const [stockFormData, setStockFormData] = useState({ code: '', averagePrice: '' });
    const [stockTableData, setStockTableData] = useState([]);


    const handleChange = (e) => {
        const { name, value } = e.target;
        setStockFormData({ ...stockFormData, [name]: value });
    };


    const submitHandler = (e) => {
        e.preventDefault();
        if (!stockFormData.code || !stockFormData.averagePrice) {
            alert("Please fill all fields!");
            return;
        }
        // setStockTableData([...stockTableData, stockFormData]);
        setStockFormData({ code: '', averagePrice: '' });
        fetch('http://www.localhost:8090/stocks/ ' + stockFormData.code)
        .then(response => response.json())
        .then(data => {
            setStockTableData(data); 
            alert(data);
            console.log(data);
        })
        .catch((error) => console.log(error));
    };

    return (
        <div className="stock-card">

            <div className="stock-search" >
                <h1 className="stock-title">Stocks</h1>

                <form method="post" onSubmit={submitHandler}>
                    <div style={{ marginBottom: 12 }}>
                        <input
                            type="text"
                            className="stock-input"
                            maxLength="5"
                            name="code"
                            onChange={handleChange}
                            placeholder="Stock's code"
                            value={stockFormData.code}
                        />
                    </div>
                    <div>
                        <input
                            type="text"
                            className="stock-input"
                            name="averagePrice"
                            onChange={handleChange}
                            placeholder="Average price"
                            value={stockFormData.averagePrice}
                        />
                    </div>
                    <div>
                        <input type="submit" name="send"></input>
                    </div>
                </form>
            </div>

            <div>
                <table className="stock-table">
                    <thead>
                        <tr>
                            <th>Code</th>
                            <th>Average Price</th>
                            <th>Current Price</th>
                            <th>Notify</th>
                        </tr>
                    </thead>

                    <tbody>
                        {stockTableData.map((item, index) => (
                            <tr key={index}>
                                <td>{item.code}</td>
                                <td>{item.averagePrice}</td>
                                <td></td>
                                <td></td>

                            </tr>
                        ))}
                        <tr>
                            <td>ABEV3</td>
                            <td>8.23</td>
                            <td>15.78</td>
                            <td>S</td>
                        </tr>
                        <tr>
                            <td>ABEV3</td>
                            <td>8.23</td>
                            <td>15.78</td>
                            <td>S</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default Stocks