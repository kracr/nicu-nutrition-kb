export class Getuserlistdata
    {
        name: string;
        firstName: string;
        lastName: string;
        repeatPassword: string;
        userName: string;
        role: string;
        email: string;
        phoneNumber: string;
        status: boolean;
        password: string;
        signature: any;
        profilePic: string;
        repDoctor: string;
        edit: boolean;
        userId: string;
        loggeduser: any;
        constructor() { 
            this.name = '';
            this.userName = '';
            this.role = '';
            this.email = '';
            this.phoneNumber = '';
            this.status = null;
            this.password = '';
            this.signature = null;
            this.profilePic = '';
            this.repDoctor = '';
            this.edit = null;
            this.userId = '';
            this.loggeduser = ''; 
        }
    }