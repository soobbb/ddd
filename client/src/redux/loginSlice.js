import { createSlice } from '@reduxjs/toolkit';

export const loginSlice = createSlice({
  name: 'login',
  initialState: false,
  reducers: {
    setLoginState: (state, action) => {
      return action.payload;
    },
  },
});

export default loginSlice.reducer;
export const { setLoginState } = loginSlice.actions;
