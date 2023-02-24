class stockObject {
    constructor(shortName, symbol, ask, change) {
        this.shortName = shortName;
        this.symbol = symbol;
        this.ask = ask;
        this.change = change;
    }

    toString() {
        return `${this.shortName}\nBuy: ${this.symbol}\n${this.ask}`;
    }

    printStockCard() {
        const rightContainer = document.querySelector('#stocks-container');
        const newLi = document.createElement('li');
        newLi.className = 'col-11 border rounded 3 m-2 p-2 d-flex justify-content-between';

        const pName = document.createElement('p');
        const pSymbol = document.createElement('p');
        const pAsk = document.createElement('p');
        const pChange = document.createElement('p');

        pName.innerText = this.shortName;
        pSymbol.innerText = this.symbol;
        pAsk.innerText = this.ask;
        pChange.innerText = this.change;

        
        if (this.change > 0) {
            pChange.className = 'green';   
        } else {
            pChange.className ='red';
        }
        

        newLi.appendChild(pSymbol);
        newLi.appendChild(pAsk);
        newLi.appendChild(pChange);


        rightContainer.appendChild(newLi);
    }
}

export default stockObject;