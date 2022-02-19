package com.jasavast.kotlinspringboilerplate.domain

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.time.LocalDate

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
class AuditEventModel (val id:String,val principal:String,
                       val auditEventDate: LocalDate= LocalDate.now()){

}