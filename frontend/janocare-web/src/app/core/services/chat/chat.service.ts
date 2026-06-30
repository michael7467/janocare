// import { Injectable, inject } from '@angular/core';
// import { BehaviorSubject, Observable, map } from 'rxjs';

// import { environment } from '../../../../environments/environment';
// import { AuthService } from '../auth';
// import { ChatMessages } from '../../models';
// import { HttpClient } from '@angular/common/http';

// @Injectable({
//   providedIn: 'root',
// })
// export class ChatService {
//   private _data$ = new BehaviorSubject<ChatMessages[]>([]);
//   private _dataSingle$ = new BehaviorSubject<ChatMessages[]>([]);
//   private _dataSent$ = new BehaviorSubject<ChatMessages>(null);
//   private _dataReceived$ = new BehaviorSubject<ChatMessages>(null);
//   private _livekitToken$ = new BehaviorSubject<string>(null);
//   private http = inject(HttpClient);
//   // private socket: Socket;
//   authToken: string;
//   userId: number;

//   constructor(public authService: AuthService) {
//     this.authService.aToken$.subscribe((token) => {
//       this.authToken = token;
//     });
//     this.authService.userProfile$.subscribe((data) => {
//       this.userId = data.id;
//     });
//     const token = localStorage.getItem('access_token');
//     this.socket = io(`${environment.chatUrl}`, {
//       transports: ['websocket', 'polling', 'flashsocket'],
//       auth: {
//         token: `Bearer ${this.authToken}`,
//       },
//     });
//   }
//   get data$() {
//     return this._data$.asObservable();
//   }
//   get dataSingle$() {
//     return this._dataSingle$.asObservable();
//   }
//   get dataSent$() {
//     return this._dataSent$.asObservable();
//   }
//   get dataReceived$() {
//     return this._dataReceived$.asObservable();
//   }
//   get livekitToken$() {
//     return this._livekitToken$.asObservable();
//   }
//   ngOnInit() {}

//   joinRoom(data): void {
//     this.socket.emit('join:room', { userId: data.room });
//     this.socket.on('chat:singleResponse', (chats) => {
//       this._dataSingle$.next(chats);
//     });
//   }
//   joinLivekitRoom(data): void {
//     this.socket.emit('livekit', { from: this.userId, to: data.to, type: data.type });
//     this.socket.on('got-token', (chats) => {
//       this._livekitToken$.next(chats.accessToken);
//       return chats;
//     });
//   }
//   receiveLivekitCall(data): void {
//     this.socket.emit('join', { from: this.userId, to: data.to, type: data.type });
//     this.socket.on('got-token', (chats) => {
//       this._livekitToken$.next(chats.accessToken);
//       return chats;
//     });
//   }

//   sendMessage(data): void {
//     this.socket.emit('send:message', data);
//     this.socket.on('chat:sendMessageResponse', (chats) => {
//       this.joinRoom({ room: data.to });
//       this._dataSent$.next(chats);
//     });
//   }
//   getJoinedLivekitCall(): Observable<any> {
//     return new Observable<any>((observer) => {
//       this.socket.on('joined', (data) => {
//         observer.next(data);
//       });
//       return () => {
//         this.socket.disconnect();
//       };
//     });
//   }
//   sendMessageFile(data): void {
//     this.socket.emit('attach:file', data);
//     this.socket.on('chat:sendMessageResponseFile', (chats) => {
//       this.joinRoom({ room: data.to });
//       this._dataSent$.next(chats);
//     });
//   }
//   sendMessageImage(data): void {
//     this.socket.emit('attach:image', data);
//     this.socket.on('chat:sendMessageResponseFile', (chats) => {
//       this.joinRoom({ room: data.to });
//       this._dataSent$.next(chats);
//     });
//   }
//   sendAudioFile(data): void {
//     this.socket.emit('attach:audio', data);
//     this.socket.on('chat:sendMessageResponseFile', (chats) => {
//       this.joinRoom({ room: data.to });
//       this._dataSent$.next(chats);
//     });
//   }
//   sendVideoFile(data): void {
//     this.socket.emit('attach:video', data);
//     this.socket.on('chat:sendMessageResponseFile', (chats) => {
//       this.joinRoom({ room: data.to });
//       this._dataSent$.next(chats);
//     });
//   }
//   replyMessage(data): void {
//     this.socket.emit('reply:to', data);
//     this.socket.on('chat:receivedMessageResponse', (chats) => {
//       this.joinRoom({ room: data.to });
//       this._dataSent$.next(chats);
//     });
//   }
//   getMessageNew(): Observable<any> {
//     return new Observable<any>((observer) => {
//       this.socket.on('receive:message', (data) => {
//         this.joinRoom({ room: data.sender?.id });
//         observer.next(data);
//       });
//       return () => {
//         this.socket.disconnect();
//       };
//     });
//   }

//   getLivekitCall(): Observable<any> {
//     return new Observable<any>((observer) => {
//       this.socket.on('call', (data) => {
//         observer.next(data);
//       });
//       return () => {
//         this.socket.disconnect();
//       };
//     });
//   }
//   getMessageNewFile(): Observable<any> {
//     return new Observable<any>((observer) => {
//       this.socket.on('receive:file', (data) => {
//         this.joinRoom({ room: data.sender?.id });
//         observer.next(data);
//       });
//       return () => {
//         this.socket.disconnect();
//       };
//     });
//   }
//   receiveMessage(receiverId: string): Observable<any> {
//     return new Observable<any>((observer) => {
//       this.socket.on('receive:message', (message) => {
//         if (message.receiverId === receiverId) {
//           observer.next(message);
//         }
//       });
//       return () => {
//         this.socket.disconnect();
//       };
//     });
//   }

//   getMessage(): Observable<any> {
//     let userId: number;
//     this.authService.userProfile$.subscribe((data) => {
//       userId = data.id;
//     });
//     return new Observable<any>((observer) => {
//       this.socket.emit('enter:chat');
//       this.socket.on('chat:response', (chats) => {
//         observer.next(chats.filter((chat) => chat?.receiver?.id !== userId));
//         this._data$.next(chats.filter((chat) => chat?.receiver?.id !== userId));
//       });
//       return () => {
//         this.socket.disconnect();
//       };
//     });
//   }

//   public upploadFile(file?: File): Observable<any> {
//     const formData = new FormData();
//     if (file) {
//       formData.append('file', file, file.name);
//     }
//     return this.http.post<any>(`${environment.apiUrl}/app/chat/upload-file`, formData).pipe(map((result) => result));
//   }
//   getRingtone(): Observable<any> {
//     return this.http.get<any>(`${environment.apiUrl}/app/chat/get-ringtone`).pipe(map((result) => result));
//   }
// }
