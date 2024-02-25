import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SaveService } from '../save.service';
import { dataURItoBlob } from '../utils';

@Component({
  selector: 'app-file-form',
  templateUrl: './file-form.component.html',
  styleUrl: './file-form.component.css'
})
export class FileFormComponent {

  //viewchile ('') match the # name of the field in the form 
  @ViewChild('imageFile') 
  imageFile!: ElementRef 

  private fb = inject(FormBuilder)
  private saveSvc = inject(SaveService)
  form!: FormGroup

  private canUpload!: boolean

  displayImg!: string

  ngOnInit(): void {
    this.form =this.fb.group({
      date: this.fb.control<Date>(new Date, [Validators.required]),
      weight: this.fb.control<number>(0, [Validators.required]),
      routine: this.fb.control<string>('', [Validators.required])
    })
  }

  process(){
    this.canUpload = true
    this.saveSvc.save(this.form, this.imageFile)
    .then(result =>{
      console.log("view fetched data", result);
        this.displayImg = result.url
        console.log('got this url>>>' , this.displayImg)
    })
    .catch(err =>{
      console.log(err)
    })
  }

}
