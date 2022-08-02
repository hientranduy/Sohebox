Sohebox Website powered by Angular

---------
- SETUP -
---------
 - Install NodeJS             ==> download https://nodejs.org/en/
 - Clone source code
 - Install Angular CLI        ==> npm install -g @angular/cli
 - Generate defaut component  ==> npm install
 - Update npm                 ==> npm install -g npm

 - Install Visual Studio Code ==> goto https://code.visualstudio.com/
   + Add extention keymaps for eclipse
   + Add extention debugger for chrome
   + Add extention preview html, hint hlml, ....
   
 - Create new variable NODEJS_HOME = "C:\Program Files\nodejs", add NODEJS_HOME to PATH  

---------------
- DEVELOPMENT -
---------------
- update C:\Windows\System32\Drivers\Etc\hosts
- Generate component         ==> ng generate component pages/crypto
- Update angular             ==> ng update --all --force
                                 ng update --all --force --allow-dirty
                                 npm update
        
- Remove package                 npm uninstall @aws-amplify/ui-angular                                   
- Update type scrip          ==> npm install @angular/flex-layout@13.0.0-beta.38 --save-dev --save-exact
- Check and fix format       ==> ng lint --fix
- Audit                      ==> npm audit fix
- Check package outupdate    ==> npm outdated 
- Check unused dependency    ==> depcheck or npm-check


- Create web icon by using https://icoconvert.com/
---------
- START -
---------
 - Start localhost          ==> ng serve --open
 - Start with host          ==> ng serve --host 0.0.0.0   open: 192.168.1.82:4200

---------------
- USEFUL LINK -
---------------
https://levelup.gitconnected.com/angular-cli-useful-commands-1023e93b843b
https://material.angular.io/components/sidenav/overview
https://www.google.com/search?q=apple&tbm=isch&hl=en&hl=en&safe=active&safe=active&tbs=isz%3Aex%2Ciszh%3A600%2Ciszw%3A800&ved=0CAEQpwVqFwoTCJDDufnJsugCFQAAAAAdAAAAABAE&biw=1349&bih=657