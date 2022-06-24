import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-food-type-dialog',
  templateUrl: './add-food-type-dialog.component.html',
  styleUrls: ['./add-food-type-dialog.component.css']
})
export class AddFoodTypeDialogComponent implements OnInit {

  @Input() title: string;
  @Input() message: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;

  constructor() { }

  ngOnInit() {
  }

}
