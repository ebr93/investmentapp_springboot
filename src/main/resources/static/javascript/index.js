//import _, { union } from 'lodash';
//import orderAPI from './marketAPIFetch.js';
//import intList from './investmentsArray';

const numberArray = [];


const addRandomNumber = () => {
  const num = Math.floor(Math.random() * 1000);
  numberArray.push(num);
}

class User {
  constructor(fname, lname, email, zip, password) {
    this.fName = fname;
    this.lName = lname;
    this.email = email;
    this.zip = zip;
    this.password = password;
  }
}

const usersDatabase = (() => {
  const addUser = (u) => {
    if (u instanceof User === false) {
      return;
    }
    // users.push(u);
    localStorage.setItem(u.email, JSON.stringify(u));
  }

  const getUser = (email) => {
    const user = JSON.parse(localStorage.getItem(email));
    console.log(user.fName, user.lName, user.email, user.password);
    // const u = new User(user.fName, user.lName, user.email, user.password);
    return user;
  }

  const deleteUser = (email) => {
    localStorage.removeItem(email);
  }

  return {
    addUser,
    getUser,
    deleteUser
  }
})()

// login validatioon for website
const userLoginValidation = (userEmail, userPassword) => {
  const u = usersDatabase.getUser(userEmail);
  console.log(u.password);
  console.log(userPassword);

  if (u === null || u.password !== userPassword || u.password === undefined || userPassword === '') {
    return false;
  } else {
    currentUser.setCurrentUser(u);
    return true;
  }
}

const userLogOff = () => {

}

// let's webpage know which user is logged in
const currentUser = (() => {
  const setCurrentUser = (u) => {
    localStorage.setItem('currentUser', JSON.stringify(u));
  }

  const userLogOff = () => {
    const visitor = new User('Visitor', '', 'upchh@example.com', '12345','12345');
    localStorage.setItem('currentUser', JSON.stringify(visitor));
  }

  const getCurrentUser = () => {
    const user = JSON.parse(localStorage.getItem('currentUser'));
    return user;
  }

  return {
    setCurrentUser,
    userLogOff,
    getCurrentUser
  }
})()

// Event Listeners for Dashboard
const dashListeners = (() => {
  const dash = document.querySelector('#main-dashboard');
  if (dash!== null) {
    const divLeft = document.querySelector('#dash-left');
    const divRight = document.querySelector('#dash-right');
    const divTop = document.querySelector('#dash-top');
    const stockButtons = document.querySelectorAll('.stock-add-btn');
    const tradedStock = document.querySelector('#traded-stock');

    const addBttn = document.querySelector('#addBttn');
    const stocksBttn = document.querySelector('#fetch-stocks');

    const numContainer = document.querySelector('#num-container');


    const loginTab = document.querySelector('#dash-tab-login');
    const signupTab = document.querySelector('#dash-tab-signup');

    /* Welcome Visitor || Welcome UserName
    window.addEventListener('load', () => {
      //console.log(`${currentUser.obj.value.fName} ${currentUser.obj.value.lName} logged in`);
      console.log(`${currentUser.getCurrentUser().fName} ${currentUser.getCurrentUser().lName} logged in`);
      const newPar = document.createElement('p');
      newPar.className = ('class col-12 d-flex justify-content-end align-items-center');

      const user = currentUser.getCurrentUser();
      newPar.innerText = `Welcome ${user.fName} ${user.lName}`;
      divTop.appendChild(newPar);

      if (user.fName !== 'Visitor') {
        loginTab.innerText = `${user.fName}`;
        loginTab.href = '#';

        signupTab.innerText = `Logout`;
        signupTab.parentElement.href = 'dashboard.html';
      }
    });
    */

    // // logs off user when LoggOff tab is clicked
    // signupTab.addEventListener('click', () => {
    //   currentUser.userLogOff();
    // });

    // addBttn.addEventListener('click', () => {
    //   addRandomNumber();

    //   const lastNum = numberArray[numberArray.length - 1];
    //   const newLi = document.createElement('li');
    //   newLi.className = ('col-3');
    //   newLi.innerText = `${lastNum}`;
    //   numContainer.appendChild(newLi);
    //   console.log(`Printing Array:\n${numberArray}`);
    // });

    // stocksBttn.addEventListener('click', () => {
    //   orderAPI.getQuote().then(respone => {
    //     console.log(`Printing Array + EventListener:\n${intList}`);
    //     for (let i = 0; i < intList.length; i++) {
    //       intList[i].printStockCard();
    //     }
    //   });
    // });

    stockButtons.forEach(stockButton => 
      stockButton.addEventListener('click', (e) => {
        console.log('this clicked');
        console.log(e.target.id);
        tradedStock.innerText = e.target.id;
      })
    ) 
  }
})()


// Event Listeners for Login Forms
const loginForm = (() => {
  const loginVal = document.querySelector('.login-validation');
  // console.log(loginVal);

  if (loginVal !== null) {
    const emailLogin = document.querySelector('#emailInput');
    const passLogin = document.querySelector('#passwordInput');
    const bttnLogin = document.querySelector('#loginBttn');

    const emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    const passRegex = /(?=^.{8,}$)(?=.*\d)(?=.*[!@#$%^&*]+)(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/;

    passLogin.addEventListener('keyup', () => {
      if (passLogin.value === '' || passLogin.value === null) {
        passLogin.className = 'form-control is-invalid';
        passLogin.setCustomValidity('Please enter your password');
      } else if (passRegex.test(passLogin.value) === false) {
        passLogin.className = 'form-control is-invalid';
        passLogin.setCustomValidity('Your password must be at least 8 characters including a lowercase letter, an uppercase letter, a special character and a number');
      } else {
        passLogin.className = 'form-control is-valid';
        passLogin.setCustomValidity('');
      }
      passLogin.reportValidity();
    });

    emailLogin.addEventListener('blur', () => {
      if (emailLogin.value === '' || emailLogin.value === null) {
        emailLogin.className = 'form-control is-invalid';
        emailLogin.setCustomValidity('Please enter your email address');
      } else if (emailRegex.test(emailLogin.value) === false) {
        emailLogin.className = 'form-control is-invalid';
        emailLogin.setCustomValidity('Please use a valid email address\n(e.g. envkt@example.com)');
      } else {
        emailLogin.className = 'form-control is-valid';
        emailLogin.setCustomValidity('');
      }
      emailLogin.reportValidity();
    });

    bttnLogin.addEventListener('click', event => {
      // userLoginValidation() is a function to validate the user input, returns true or false
      if (userLoginValidation(emailLogin.value, passLogin.value)) {
        window.open('dashboard.html', '_self');
        event.preventDefault();
        event.preventDefault();
      } else {
        event.preventDefault();
        event.stopPropagation();

        emailLogin.className = 'form-control is-invalid';
        passLogin.className = 'form-control is-invalid';
      }
    });
  }
})()


// Event Listeners for SignUp Forms
const signupForm = (() => {
  const signupVal = document.querySelector('.signup-validation');
  if (signupVal !== null) {
    const fName = document.querySelector('#fName');
    const lName = document.querySelector('#lName');
    const email = document.querySelector('#newEmail');
    //const zip = document.querySelector('#zipInput');
    const pass = document.querySelector('#createPassword');
    const confirmPass = document.querySelector('#confirmPassword');
    const bttnSignup = document.querySelector('#signupBttn');

    const nameRegex = /^[a-zA-Z]{2,30}$/;
    const emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    //const zipRegex = /^\d{5}$/;
    const passRegex = /(?=^.{8,}$)(?=.*\d)(?=.*[!@#$%^&*]+)(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/;

    fName.addEventListener('blur', () => {
      if (fName.value === '' || fName.value === null) {

        fName.className = 'form-control is-invalid';
        fName.setCustomValidity('Please enter your first name');
      } else if (nameRegex.test(fName.value) === false) {
        fName.className = 'form-control is-invalid';
        fName.setCustomValidity('Please enter a name between 2 and 30 characters long');
      } else {
        fName.className = 'form-control is-valid';
        fName.setCustomValidity('');
      }
      fName.reportValidity();
    });

    lName.addEventListener('blur', () => {
      if (lName.value === '' || lName.value === null) {
        lName.className = 'form-control is-invalid';
        lName.setCustomValidity('Please enter your last name');
      } else if (nameRegex.test(lName.value) === false) {
        lName.className = 'form-control is-invalid';
        lName.setCustomValidity('Please enter a name between 2 and 30 characters long');
      } else {
        lName.className = 'form-control is-valid';
        lName.setCustomValidity('');
      }
      lName.reportValidity();
    });

    email.addEventListener('blur', () => {
      if (email.value === '' || email.value === null) {
        email.className = 'form-control is-invalid';
        email.setCustomValidity('Please enter your email address');
      } else if (emailRegex.test(email.value) === false) {
        email.className = 'form-control is-invalid';
        email.setCustomValidity('Please use a valid email address\n(e.g. envkt@example.com)');
      } else {
        email.className = 'form-control is-valid';
        email.setCustomValidity('');
      }
      email.reportValidity();
    });

    pass.addEventListener('keyup', () => {
      if (pass.value === '' || pass.value === null) {
        pass.className = 'form-control is-invalid';
        pass.setCustomValidity('Please enter your password');
      } else if (passRegex.test(pass.value) === false) {
        pass.className = 'form-control is-invalid';
        pass.setCustomValidity('Your password must be at least 8 characters including a lowercase letter, an uppercase letter, a special character and a number');
      } else {
        pass.className = 'form-control is-valid';
        pass.setCustomValidity('');
      }
      pass.reportValidity();
    });

    confirmPass.addEventListener('keyup', () => {
      if (confirmPass.value === '' || confirmPass.value === null) {
        confirmPass.className = 'form-control is-invalid';
        confirmPass.setCustomValidity('Please confirm your password');
      } else if (confirmPass.value !== pass.value) {
        confirmPass.className = 'form-control is-invalid';
        confirmPass.setCustomValidity('Passwords do not match\nYour password must be at least 8 characters including a lowercase letter, an uppercase letter, a special character and a number');
      } else {
        confirmPass.className = 'form-control is-valid';
        confirmPass.setCustomValidity('');
      }
      confirmPass.reportValidity();
    });

    bttnSignup.addEventListener('click', event => {
      // INPUT IS NECESARY TO BE VALIDATED
      if (fName.value !== '' && lName.value !== '' && email.value !== '' && pass.value !== '' && confirmPass.value !== '' && pass.value === confirmPass.value) {
        // const newUser = new User(fName.value, lName.value, email.value, zip.value, pass.value);
        // usersDatabase.addUser(newUser);
      } else {
        console.log('else button listener');
        event.preventDefault();
        event.stopPropagation();
      }
    });
  }
})()

