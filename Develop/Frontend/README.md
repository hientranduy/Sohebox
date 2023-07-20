Sohebox Website powered by Angular

---------
- SETUP -
---------
 - Install NodeJS              ==> download https://nodejs.org/en/
 - Create variable NODEJS_HOME = "C:\Program Files\nodejs", add NODEJS_HOME to PATH  
 - Install Visual Studio Code  ==> goto https://code.visualstudio.com/

 - Install sohebox             ==> npm install --save --legacy-peer-deps


---------------
- DEVELOPMENT -
---------------
- Generate component         ==> ng generate component pages/crypto
- Update angular             ==> ng update @angular/core@14 @angular/cli@14 --allow-dirty --force
                                 ng update @angular/cdk@15         --allow-dirty --force
                                 ng update @angular/material@15    --allow-dirty --force
                                 ng update @angular/flex-layout@15 --allow-dirty --force
                                 ng add @ng-bootstrap/ng-bootstrap
                                 ng update
- Check all version of current Angular version ==> ng update             
- Check package outupdate                      ==> npm outdated 
- Check unused dependency                      ==> depcheck or npm-check
- Format all source code    npx prettier --write "**/*.ts" "**/*.css" "**/*.html" "**/*.json"


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