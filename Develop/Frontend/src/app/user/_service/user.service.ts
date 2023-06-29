import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserSCO } from '@app/_common/_sco';
import { environment } from '@environments/environment';
import { User } from '../_models';

@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private http: HttpClient) {
    }

    // Register user
    register(user: User) {
        return this.http.post(`${environment.soheboxUrl}/users`, user);
    }

    // Logout
    logout() {
        return this.http.post(`${environment.soheboxUrl}/users/logout`, null);
    }

    // Change password by logged user
    changePasswordByLoggedUser(user: User) {
        return this.http.put(`${environment.soheboxUrl}/users/changePasswordLoggedUser`, user);
    }

    // Delete logged user
    deleteLoggedUser() {
        return this.http.delete(`${environment.soheboxUrl}/users`);
    }

    // Update user
    updateUser(user: User) {
        return this.http.put(`${environment.soheboxUrl}/users`, user);
    }

    // Search user
    searchUserStatus(sco: UserSCO) {
        return this.http.post(`${environment.soheboxUrl}/users/status`, sco);
    }

    // Search user
    searchActiveUser(sco: UserSCO) {
        return this.http.post(`${environment.soheboxUrl}/users/activeUser`, sco);
    }

    // Get by id
    getById(id: number) {
        return this.http.get(`${environment.soheboxUrl}/users/${id}`);
    }

    // Change private key
    changePrivatekey(user: User) {
        return this.http.put(`${environment.soheboxUrl}/users/changePrivateKey`, user);
    }
}
