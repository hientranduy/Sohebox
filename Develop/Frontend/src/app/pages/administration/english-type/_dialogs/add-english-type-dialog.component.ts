import { Component, Input, OnInit } from "@angular/core";

@Component({
  selector: "app-add-english-type-dialog",
  templateUrl: "./add-english-type-dialog.component.html",
  styleUrls: ["./add-english-type-dialog.component.css"],
})
export class AddEnglishTypeDialogComponent implements OnInit {
  @Input() title: string;
  @Input() message: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;

  constructor() {}

  ngOnInit() {}
}
