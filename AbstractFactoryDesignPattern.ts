interface Bank{
    getBankDetails(): string;
    getIntrestRate(): number;
}

abstract class AbstractFactory{
    abstract getBank(bank: string):Bank;
    abstract getLoan(loan: string):Loan;
}

class KOTAK implements Bank{
   
    getBankDetails(): string{
        return "KOTAK Bank";
    }

    getIntrestRate():number{
        return 8.4;
    }
}

class IDFC implements Bank{
    getBankDetails(): string{
        return "IDFC Bank";
    }
    getIntrestRate():number{
        return 7.4;
    }
}

abstract class Loan{
    protected rate: number;
    abstract setIntrestRate(rate : number):void;
    public calculateLoadPayment(loanamount:number, years:number):void{
        let EMI:number;
        let n:number;

        n = years * 12;
        this.rate = this.rate/12;
        EMI = ((this.rate * Math.pow((1+this.rate),n)) / ((Math.pow((1+this.rate),n))-1)) * loanamount;  
        console.log("your monthly EMI is "+ EMI +" for the amount "+loanamount+" you have borrowed");
    }
}

class  HomeLoan extends Loan{
    public setIntrestRate(r:number){
        this.rate = r;
    }
}
class  BusinessLoan extends Loan{
    public setIntrestRate(r:number){
        this.rate = r;
    }
}    

class BankFactory extends AbstractFactory{
    getBank(bank:string): Bank{  
        if(bank == null){  
           return null;  
        }  
        if(bank=="KOTAK"){  
           return new KOTAK();  
        } else if(bank=="IDFC"){  
           return new IDFC();  
        }  
        return null;  
     }  
    getLoan(loan:string):Loan {  
        return null;  
     }  
}

class LoanFactory extends AbstractFactory{
    getBank(bank:string): Bank{  
        return null;  
     }  
    getLoan(loan:string):Loan {  
        
        if(loan == null){  
            return null;  
         }  
         if(loan=="Home"){  
            return new HomeLoan();  
         } else if(loan=="Business"){  
            return new BusinessLoan();  
         }  
         return null;  

     }  
}


class FactoryCreator {  
    public static getFactory(choice:string):AbstractFactory{  
     if(choice == "Bank"){  
        return new BankFactory();  
     } else if(choice == "Loan"){  
        return new LoanFactory();  
     }  
     return null;  
  }  
}


//console.log("Enter the name of Bank from where you want to take loan amount: ");  
let bankName: string ="IDFC";  
  
//console.log("\n");  
//console.log("Enter the type of loan e.g. home loan or business loan or education loan : ");  
  
let loanName : string = "Home";  
let bankFactory : AbstractFactory = FactoryCreator.getFactory("Bank");  
let b: Bank = bankFactory.getBank(bankName);  
  
//console.log("\n");  
//console.log("Enter the interest rate for "+b.getBankDetails()+ ": ");  
  
let rate : number = 23;  
//console.log("\n");  
//console.log("Enter the loan amount you want to take: ");  
  
let loanAmount : number = 40000;  
//console.log("\n");  
//console.log("Enter the number of years to pay your entire loan amount: ");  
let years : number = 3;  
  
console.log("\n");  
console.log("you are taking the loan from "+ b.getBankDetails());  
  
let loanFactory : AbstractFactory = FactoryCreator.getFactory("Loan");  

let l : Loan = loanFactory.getLoan(loanName);
l.setIntrestRate(rate);  
l.calculateLoadPayment(loanAmount,years);


/**** Below is the example for closures */

var addTo = function(passed){

    var add = function(inner){
        return passed + inner;
    }

    return add;
}

var addThree = addTo(3);
var addFour = addTo(4);


console.log(addThree(1));
console.log(addFour(1));














