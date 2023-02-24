import stockObject from "./stockobject";
import intList from "./investmentsArray";

const orderAPI = (() => {
  const options = {
    method: 'GET',
    headers: {
      'X-RapidAPI-Key': '66d7085b62msh288087059e8ae40p1c5d91jsnb5aa211a244e',
      'X-RapidAPI-Host': 'apidojo-yahoo-finance-v1.p.rapidapi.com'
    } 
  };

  async function getQuote() {
    try {
      const response = await fetch('https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=US&symbols=AMD%2CIBM%2CAAPL%2CTSLA%2CAMZN%2CMSFT%2CGOOGL', options);
      const responseData = await response.json();
      console.log(responseData);

      //const rightContainer = document.querySelector('#stocks-container');
      console.log(responseData.quoteResponse.result[0]);
      console.log(responseData.quoteResponse.result[1]);
      console.log(responseData.quoteResponse.result[2]);
       
      // quoteResponse allows us to access the result of the API call
      const stockData = responseData.quoteResponse;

      for (let i = 0; i < 7; i++) {
        const newStockObject = new stockObject(stockData.result[i].shortName, stockData.result[i].symbol, stockData.result[i].ask, stockData.result[i].regularMarketChangePercent);
        intList.push(newStockObject);
        console.log(`Inside Loop + getQuote(): ${intList}`);
      }
      console.log(`Outside Loop + getQuote(): ${intList}`);

      return responseData;
    } catch (err) {
      console.error(err);
    }
  }

  return {
    getQuote
  }
})()

export default orderAPI;