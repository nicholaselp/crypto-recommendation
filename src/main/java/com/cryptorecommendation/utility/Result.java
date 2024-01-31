package com.cryptorecommendation.utility;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**The result of an operation, or an error**/
public class Result<ResultT, ErrorT> {
    private final boolean success;
    private final ResultT value;
    private final ErrorT error;

    private Result(boolean success, ResultT value, ErrorT error){
        this.success = success;
        this.value = value;
        this.error = error;
    }

    /** Returns: indicates the success result **/
    public boolean isSuccess(){ return success; }

    /** Return: indicates the failure result **/
    public boolean isFail(){ return !isSuccess(); }

    /** Returns: result if it is present **/
    public Optional<ResultT> getValue(){ return Optional.ofNullable(value); }

    /** Forces to return the result. If there were an error - throws execption.
     *  Use this method only after succesful check of isSuccess() **/
    public ResultT getSuccessValue(){
        if(!isSuccess()){
            throw new IllegalArgumentException("The result of the operation is not success");
        }
        return value;
    }


    /** Returls: error if present **/
    public Optional<ErrorT> getError(){ return Optional.ofNullable(error); }

    /** Executes some action on a success result. if there is one. Returns itself. This method is
     * supposed to be used in result handling pipelines. Example, result.ifSuccess(...).ifError(...) **/
    public Result<ResultT, ErrorT> ifSuccess(Consumer<ResultT> successFlow){
        if(success){
            successFlow.accept(value);
        }
        return this;
    }

    /** Executes some action on an error if there is one. Returns itself. This method is supposed
     *  to be used in result handling pipeline. Example, result.ifSuccess(...).ifError(...) **/
    public void isError(BiConsumer<ErrorT, ResultT> errorFlow){
        if(!success){
            errorFlow.accept(error, value);
        }
    }

    /** Constructs the success result with a given value **/
    public static<T, E> Result<T, E> success(T value){
        requireNonNull(value, "Result must not be null");
        return new Result<>(true, value, null);
    }

    /** Construct the sucess with no result **/
    public static <E> Result<Nothing, E> success(){ return success(Nothing.INSTANCE); }

    /** Construct the failure with a given error **/
    public static<T, E> Result<T, E> fail(E error){
        requireNonNull(error, "Error must not be null");
        return new Result<>(false, null, error);
    }

    /** Construct the failure with a given error and result. Might be used when you have some partial result even in case of failure **/
    public static<T,E> Result<T,E> fail(E error, T value){
        requireNonNull(error, "Error must not be null");
        return new Result<>(false, value, error);
    }

    /** Shortcut to construct failure with string error message **/
    public static <T> Result<T, String> fail(String error){
        if(isBlank(error)){
            throw new IllegalArgumentException("Error must not be blank");
        }
        return new Result<>(false, null, error.trim());
    }

    /** Shortcut to construct failure with string error message and result **/
    public static <T> Result<T, String> fail(String error, T value){
        if (isBlank(error)) {
            throw new IllegalArgumentException("Error must not be blank");
        }
        return new Result<>(false, value, error.trim());
    }

    @Override
    public String toString(){
        return "Result{" +
                "success=" + success +
                ", value=" + value +
                ", error='" + error + '\'' +
                '}';
    }

    /** Safely maps a success result and error to different types. i case of exception returns failure **/
    public <NewResultT> Result<NewResultT, ErrorT> map(Function<ResultT, NewResultT> resultMapper, Function<Exception, ErrorT> exceptionMapper){
        if(!isSuccess()){
            return fail(error);
        }

        try {
            return success(resultMapper.apply(value));
        } catch (Exception exception){
            return fail(exceptionMapper.apply(exception));
        }
    }

    /** Safely maps a success result and error to different types. I case of exception returns failure **/
    public <NewResultT, NewErrorT> Result<NewResultT, NewErrorT> map(Function<ResultT, NewResultT> resultMapper,
                                                                     Function<ErrorT, NewErrorT> errorMapper,
                                                                     Function<Exception, NewErrorT> exceptionMapper){
        if(success){
            try {
                return success(resultMapper.apply(value));
            } catch (Exception exception){
                return fail(exceptionMapper.apply(exception));
            }
        } else {
            try {
                return fail(errorMapper.apply(error));
            } catch (Exception exception){
                return fail(exceptionMapper.apply(exception));
            }
        }
    }

    /** Safely maps a sucecss result to different type, unwrapping from additional result, if the
     * mapping function returns result In case of exception returns failure **/
    public <NewResultT> Result<NewResultT, ErrorT> flatMap(Function<? super ResultT, ? extends Result<? extends NewResultT, ErrorT>> resultMapper,
                                                          Function<Exception, ErrorT> exceptionMapper){
        if(!success){
            return fail(error);
        }
        try {
            return (Result<NewResultT, ErrorT>) resultMapper.apply(value);
        } catch (Exception exception){
            return fail(exceptionMapper.apply(exception));
        }
    }

    /** Safely maps an error to different types. In case of exception returns failure **/
    public <NewErrorT> Result<ResultT, NewErrorT> mapError(Function<ErrorT, NewErrorT> errorMapper,
                                                           Function<Exception, NewErrorT> exceptionMapper){
        if(success){
            return success(value);
        }

        try{
            return fail(errorMapper.apply(error));
        } catch (Exception exception){
            return fail(exceptionMapper.apply(exception));
        }
    }



}
