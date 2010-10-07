%struct.FILE = type { i8*, i32, i8*, i32, i32, i32, i32, i8* }
%struct.stat = type { i32, i16, i16, i16, i16, i16, i32, i32, i32, i32, i32 }

declare i32 @fseek(%struct.FILE*, i32, i32) nounwind
declare i32 @fread(i8*, i32, i32, %struct.FILE*) nounwind
declare i32 @puts(i8*)
declare void @exit(i32) noreturn nounwind
declare %struct.FILE* @fopen(i8*, i8*) nounwind
declare i32 @printf(i8*, ...) nounwind 
declare i64 @"\01_clock"()
declare void @llvm.memcpy.i32(i8* nocapture, i8* nocapture, i32, i32) nounwind
declare i32 @fprintf(%struct.FILE*, i8*, ...) nounwind
declare %struct.FILE* @fopen(i8*, i8*) nounwind
declare i32 @clock() nounwind
declare i32 @fflush(%struct.FILE*) nounwind
declare i32 @fclose(%struct.FILE*) nounwind