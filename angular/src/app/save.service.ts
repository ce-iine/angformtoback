import { HttpClient } from '@angular/common/http';
import { ElementRef, Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { dataURItoBlob } from './utils';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class SaveService {

  private http = inject(HttpClient)

  save(form: FormGroup, imageFile:ElementRef): Promise<any> { // url: string

    const values = form.value

    console.info("FORM VALUE>>>>>>", values)
    console.info("SEE IMGAGE ELEMENT REF>>>>>>", imageFile.nativeElement.files[0])

    const dataForm = new FormData() // // when angular sends formData to server, client will know to send a multipartform (header)
    dataForm.set('date', values.date)
    dataForm.set('weight', String(values.weight))
    dataForm.set('routine', values.routine)
    dataForm.set('imageFile', imageFile.nativeElement.files[0])

    console.info("BUILT FINISH >>>>>>", dataForm) //

    // const blob = dataURItoBlob(url)
    // const file = new File([blob], 'captured.jpg', { type: "image/jpg" })
    // console.info('file>>>>>', file)
    return firstValueFrom(this.http.post<any>('http://localhost:8080/process', dataForm))
  }
}
