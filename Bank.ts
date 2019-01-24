interface Bank{
    getBankDetails(): void;
    getIntrestRate(): number;
}

class SBI implements Bank{
   
    getBankDetails(): void{
        console.log("SBI Bank");
    }

    getIntrestRate():number{
        return 8.4;
    }
}
class HDFC implements Bank{
    getBankDetails(): void{
        console.log("HDFC Bank");
    }
    getIntrestRate():number{
        return 7.4;
    }
}


class BankObjectGenerator {

    getBankObject(bankName: string) : Bank{

        var b : Bank;
        if(bankName == "SBI"){
            b = new SBI();
        } else if(bankName == "HDFC"){
            b = new HDFC();
        }
        return b;
    }

}

window.onload = () => {

    var bn = prompt("Enter Your Bank Name");
    var bankObjGen = new BankObjectGenerator();
    var banObj : Bank = bankObjGen.getBankObject(bn);
    banObj.getBankDetails();
    var intresRate = banObj.getIntrestRate();

    var span1 = document.createElement("span");
    span1.style.color = "RED";
    span1.innerText = intresRate.toString();
    document.body.appendChild(span1);

}


